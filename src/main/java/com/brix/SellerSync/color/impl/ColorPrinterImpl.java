package com.brix.SellerSync.color.impl;

import com.brix.SellerSync.color.BluePrinter;
import com.brix.SellerSync.color.ColorPrinter;
import com.brix.SellerSync.color.GreenPrinter;
import com.brix.SellerSync.color.RedPrinter;

public class ColorPrinterImpl implements ColorPrinter {
    private RedPrinter redPrinter;
    private GreenPrinter greenPrinter;
    private BluePrinter bluePrinter;
    
    public ColorPrinterImpl(RedPrinter redPrinter, GreenPrinter greenPrinter, BluePrinter bluePrinter) {
        this.redPrinter = redPrinter;
        this.greenPrinter = greenPrinter;
        this.bluePrinter = bluePrinter;    
    }
    
    @Override
    public String print() {
        return String.join(",", redPrinter.print(), greenPrinter.print(), bluePrinter.print());
    }

}
