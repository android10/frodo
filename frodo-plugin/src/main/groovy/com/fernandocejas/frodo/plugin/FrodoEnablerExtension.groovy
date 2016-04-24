package com.fernandocejas.frodo.plugin

class FrodoEnablerExtension {
  def enabled = true

  def setEnabled(boolean enabled) {
    this.enabled = enabled
  }

  def getEnabled() {
    return enabled;
  }
}
