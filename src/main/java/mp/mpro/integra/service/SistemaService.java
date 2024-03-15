package mp.mpro.integra.service;

import jakarta.transaction.Transactional;
import java.util.List;
import mp.mpro.integra.web.rest.SistemaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SistemaService {

    public List<String> getGroups() {
        return null;
    }
}
