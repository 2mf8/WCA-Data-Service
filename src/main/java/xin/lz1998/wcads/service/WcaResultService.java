package xin.lz1998.wcads.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WcaResultService {
    Page findResultsByPersonIdAndEventId(String personId, String eventId, Pageable pageable);
    Page findWcaResultsByPersonId(String personId,Pageable pageable);
}
