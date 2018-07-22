package com.marshallaf.mobileui.test.factory

import com.marshallaf.mobileui.model.UiProject

object ProjectFactory {

  fun makeUiProject(): UiProject {
    return UiProject(DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(),
        DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomString(), DataFactory.randomBoolean())
  }
}
