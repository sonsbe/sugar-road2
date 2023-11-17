<template>
  <div class="app-body">
    <div class="content">
      <router-view />
    </div>
    <Nav></Nav>
  </div>
</template>

<script setup>
  import Nav from '@/components/ui/Nav.vue'
</script>
<style>
  #app {
    margin : 0;
    padding : 0;
  }
</style>
<style>
  @import "@/assets/ui.css";
</style>


