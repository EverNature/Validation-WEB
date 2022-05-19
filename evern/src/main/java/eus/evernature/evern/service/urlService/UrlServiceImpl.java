package eus.evernature.evern.service.urlService;

import javax.servlet.http.HttpServletRequest;

public class UrlServiceImpl implements UrlService {

    @Override
    public String getSiteUrl(HttpServletRequest req) {
        String siteUrl = req.getRequestURL().toString();
        return siteUrl.replace(req.getServletPath(), "");
    }
    
}
