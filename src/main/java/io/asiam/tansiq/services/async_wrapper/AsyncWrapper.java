package io.asiam.tansiq.services.async_wrapper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncWrapper {
    @Async
    public void wrapper(Runnable r) {
        r.run();
    }
}
