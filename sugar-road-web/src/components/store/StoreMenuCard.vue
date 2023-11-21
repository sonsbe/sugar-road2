<template>
  <div class="menuItem">
    <img
      v-bind:src="`http://localhost:1023${menuInfo.menuImagePath}`"
      alt="img"
    />
    <p>{{ menuInfo.menuName }}</p>
  </div>
</template>

<script setup>
import { defineProps, onMounted } from "vue";
const { menuInfo } = defineProps(["menuInfo"]);
onMounted(() => {
  console.log(menuInfo.menumenuImagePath);
});
</script>

<style scoped>
img {
  width: 35vw;
  height: 15vh;
  object-fit: cover;
}
.menuItem {
  margin-right: 10px;
  border: 1px solid lightgrey;
  border-radius: 5px;
}

.menuItem > p {
  text-align: center;
  line-height: 23px;
  height: 23px;
  font-size: 14px;
}
</style>
