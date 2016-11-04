package com.example.rahul.test;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Created by rahul on 1/12/14.
 */
public class FullTestSuite extends TestSuite {

    public static Test suite(){
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    FullTestSuite(){
        super();
    }
}
