<template>
  <RouterLink :to="'/store/' + p.object.storeId">
    <div class="storeBoxTop">
      <div class="store-like">
        <span>{{ p.object.storeName }}</span>
        <span v-text="p.object.recommendCount"></span>
      </div>
    </div>
    <img
      :src="`http://localhost:1023${p.object.storeImagePath}`"
      class="storeImg"
      alt="가게 대표 이미지"
    />
    <div v-text="p.object.storeName" class="store-name"></div>
  </RouterLink>
</template>
<script setup>
import { defineProps } from "vue";

const p = defineProps({
  object: {
    type: Object,
  },
});
console.log("p", p.object);
</script>
<style scoped>
.store-like > span {
  text-align: left;
}
</style>
