package com.brix.SellerSync.color.impl;

import com.brix.SellerSync.color.BluePrinter;
import com.brix.SellerSync.color.ColorPrinter;
import com.brix.SellerSync.color.GreenPrinter;
import com.brix.SellerSync.color.RedPrinter;

public class ColorPrinterImpl implements ColorPrinter {
    private RedPrinter redPrinter;
    private GreenPrinter greenPrinter;
    private BluePrinter bluePrinter;
    
    public ColorPrinterImpl(){
        this.redPrinter = new EnglishRedPrinter();
        this.greenPrinter = new EnglishGreenPrinter();
        this.bluePrinter = new EnglishBluePrinter();
    }
    
    
    @Override
    public String print() {
        return String.join(",", redPrinter.print(), greenPrinter.print(), bluePrinter.print());
    }

}
