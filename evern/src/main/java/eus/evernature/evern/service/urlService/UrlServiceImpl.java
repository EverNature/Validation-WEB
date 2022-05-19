package eus.evernature.evern.service.urlService;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Transactional
public class UrlServiceImpl implements UrlService {

    @Override
    public String getSiteUrl(HttpServletRequest req) {
        String siteUrl = req.getRequestURL().toString();
        return siteUrl.replace(req.getServletPath(), "");
    }
    
}
