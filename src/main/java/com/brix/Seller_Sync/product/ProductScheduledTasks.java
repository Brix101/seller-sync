package com.brix.Seller_Sync.product;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.brix.Seller_Sync.amzn.payload.CreateReportResponse;
import com.brix.Seller_Sync.amzn.payload.CreateReportSpecification;
import com.brix.Seller_Sync.amzn.payload.CreateReportSpecification.ReportType;
import com.brix.Seller_Sync.amzn.payload.Report;
import com.brix.Seller_Sync.amzn.payload.ReportDocument;
import com.brix.Seller_Sync.amzn.service.AmznSPReportService;
import com.brix.Seller_Sync.client.Client;
import com.brix.Seller_Sync.client.service.ClientService;
import com.brix.Seller_Sync.marketplace.Marketplace;
import com.brix.Seller_Sync.product.service.ListingService;
import com.brix.Seller_Sync.product.service.ProductService;

import lombok.extern.java.Log;

@Component
@Log
public class ProductScheduledTasks {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AmznSPReportService amznSPReportService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private ProductService productService;

    private static final ConcurrentHashMap<String, String> reportQueue = new ConcurrentHashMap<>();

    @Scheduled(cron = "0 0 0 * * ?") // This cron expression means every day at midnight
    public void createListingReport() {
        // TODO move this to a service with a filter for all the store clients
        List<Client> clients = clientService.getAllSPClientsToken();

        for (Client client : clients){
            List<Marketplace> marketplaces = client.getStore().getMarketplaces();

            List<String> marketplaceIds = marketplaces.stream().map(Marketplace::getMarketplaceId).collect(Collectors.toList());

            CreateReportSpecification createReportSpecification = new CreateReportSpecification();
            createReportSpecification.setReportType(ReportType.GET_MERCHANT_LISTINGS_ALL_DATA);
            createReportSpecification.setMarketplaceIds(marketplaceIds);

            CreateReportResponse createReportResponse = amznSPReportService.createReport(client, createReportSpecification);

            String reportKey = client.getClientId() + ":" + createReportResponse.hashCode();
            if (createReportResponse.getReportId() != null && !reportQueue.containsKey(reportKey)){
                enqueueReport(reportKey, createReportResponse.getReportId());
            }
        }
    }

    @Scheduled(fixedDelay = 5000) // This cron expression means every 5 seconds
    public void getAllReports(){
        if (!reportQueue.isEmpty()){
            for (String key : reportQueue.keySet()){
                String clientId = key.split(":")[0];
                Client client = clientService.getClientByClientId(clientId);
                String reportId = reportQueue.get(key);

                CreateReportResponse createReportResponse = new CreateReportResponse(reportId);

                Report report = amznSPReportService.getReport(client, createReportResponse);

                if (report.getReportDocumentId() != null){
                    ReportDocument reportDocument = amznSPReportService.getReportDocument(client, report);
                    List<Listing> listings = listingService.parseListingDocument(reportDocument);

                    log.info("Update listings for client: " + client.getClientId());
                    for (Listing listing : listings){
                        listing.setStoreId(client.getStore().getId());
                        productService.upsertListing(listing);
                    }
                    
                    dequeueReport(key);
                }
            }
        }
    }

    private void enqueueReport(String key, String reportId){
        reportQueue.put(key, reportId);
    }

    private void dequeueReport(String key){
        reportQueue.remove(key);
    }

}