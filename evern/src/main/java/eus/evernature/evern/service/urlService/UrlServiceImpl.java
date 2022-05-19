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
        return req.getRequestURL().toString();
    }
    
}
