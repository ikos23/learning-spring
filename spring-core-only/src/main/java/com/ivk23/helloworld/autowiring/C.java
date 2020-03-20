package com.ivk23.helloworld.autowiring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class C {

    private static final Logger LOG = LoggerFactory.getLogger(C.class);

    private A a;
    private B b;

    /*
     * Current documentation and JavaDoc says
     * that multiple @Autowired annotated
     * constructors are possible, but only one must be
     * required.
     *
     * But! The only way to make it work is to have them all
     * @Autowired(required = false) or to have a
     * single @Autowired annotated constructor per class.
     *
     * Here is an issue I reported (regarding this):
     * https://github.com/spring-projects/spring-framework/issues/24711
     */

    @Autowired(required = false)
    public C(A a) {
        this.a = a;
    }

    @Autowired(required = false)
    public C(B b) {
        this.b = b;
    }

    @Autowired(required = false)
    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public void exec() {
        if (a != null) a.exec();
        else LOG.info("$ C.a is null.");

        if (b != null) b.exec();
        else LOG.info("$ C.b is null.");

        LOG.info("$ C.exec() called.");
    }

}
