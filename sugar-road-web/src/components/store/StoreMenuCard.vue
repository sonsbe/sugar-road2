<template>
  <div class="menuItem">
    스토어 메뉴 카드
    <img v-bind:src="menuInfo.menumenuImagePath" />
    <p>메뉴 이름:{{ menuInfo.menuName }}</p>
  </div>
</template>

<script setup>
import { defineProps, onMounted } from "vue";
const { menuInfo } = defineProps(["menuInfo"]);
// onMounted(() => {
//   console.log(menuInfo);
// });
</script>

<style scoped>
img {
  width: 100px;
  height: 100px;
  border: 1px solid black;
}
.menuItem {
  width: 150px;
  height: 150px;
  border: 1px solid green;
}
</style>
