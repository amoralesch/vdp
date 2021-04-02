package com.amoralesch.vdp.spec.api;

import org.concordion.api.MultiValueResult;

public class GetStatusFixture extends ApiBase {
  public MultiValueResult http(String method, String uri)
    throws Exception
  {
    return http(method, uri, null, null);
  }
}
