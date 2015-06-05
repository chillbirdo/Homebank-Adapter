package com.gf.doughflow.downloader;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

public class EasyBankDownloader implements IDownloader {

    public static void main(String[] args){
        new EasyBankDownloader().download();
    }
    
    @Override
    public void download() {
        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_8);
//        webClient.setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getCookieManager().setCookiesEnabled(true);

        try{
            final HtmlPage page1 = webClient.getPage("https://ebanking.easybank.at/InternetBanking/InternetBanking?d=login&svc=EASYBANK&ui=html&lang=en");
            HtmlElement button = (HtmlElement)page1.getByXPath( "//div[@class='dynamic-btn fright nomargin']").get(0);
            
            final HtmlForm form = page1.getFormByName("loginForm");
            final HtmlTextInput textField = form.getInputByName("dn");
            final HtmlPasswordInput pwd = form.getInputByName("pin");

            textField.setValueAttribute("xxxx");
            pwd.setValueAttribute("xxxs");
//
            final HtmlPage page2 = button.click();
            System.out.println(page2.asXml());

            
            
//            String htmlBody = page2.getWebResponse().getContentAsString();
//
//            System.out.println("Base Uri 1 : " + page1);
//            System.out.println("Base Uri 2 : " + page2);
//
//            webClient.closeAllWindows();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
