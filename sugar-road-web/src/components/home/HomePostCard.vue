<template>
  <RouterLink :to="'/post/' + p.object.id">
    <div class="storeBoxTop">
      <div class="store-like">
        👍<span v-text="p.object.recommendCount"></span>
      </div>
    </div>
    <img
      :src="`http://localhost:1023${
        p.object.postImage.length > 0
          ? p.object.postImage[0]
          : '/images/93c339e1-eb4d-4d39-b773-bd50913b7dc1thymeleaf-logo-6E4D42A713-seeklogo.com.png'
      }`"
      class="storeImg"
      alt="게시글 이미지"
    />
    <div v-text="p.object.title" class="store-name">
    </div>
  </RouterLink>
</template>
<script setup>
import { defineProps } from "vue";

const p = defineProps({
  object: {
    type: Object,
  },
});
</script>
