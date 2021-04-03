package com.amoralesch.vdp.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class StatusControllerTest {
  private final StatusController controller = new StatusController();

  @Test
  void get_ReturnsServerUpStatus_Always()
  {
    StatusController.Status s = controller.get();

    assertThat(s).isNotNull();
    assertThat(s.getStatus()).isEqualTo("Server Up");
  }
}
