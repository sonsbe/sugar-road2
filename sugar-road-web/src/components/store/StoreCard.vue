<template>
  <div class="storeBox">
    <router-link :to="'/store/'+ storedId">
      <a href="#">
        <div class="storeBoxTop">
          <p>storeId: {{ storeData.storeId }}</p>
          <h3>가게명: {{ storeData.storeName }}</h3>
          <div><span>추천수</span>, <span>리뷰수</span></div>
        </div>
        <img class="storeImg" v-bind:src="storeData.storeImagePath" />
        {{ storeData.storeImagePath}}
        <div class="store-desc">가게 설명:{{ storeData.storeDesc }}</div>
      </a>
    </router-link>
  </div>
</template>
<script setup>
import { defineProps } from "vue";
import { ref } from "vue";
const { storeData } = defineProps(["storeData"]);
let storedId = ref(storeData.storeId);
</script>
<style scoped>
a {
  text-decoration: none;
}
.storeBox {
  border: 2px solid lightgray;
  padding: 13px 8px;
  margin-bottom: 2vh;
  border-radius: 10px;
  transition: 0.5s ease;
  cursor: pointer;
  box-shadow: rgba(0, 0, 0, 0.15) 2.95px 2.95px 3.6px;
}
.storeBox > a {
  text-decoration: none;
}
.storeBox:hover {
  box-shadow: 1px 1px #e1d5ed, 2px 2px #e1d5ed, 3px 3px #e1d5ed, 4px 4px #e1d5ed,
    5px 5px #e1d5ed, 6px 6px #e1d5ed;
}
.storeBoxTop {
  display: flex;
  justify-content: space-between;
  height: 4vh;
}
.storeBoxTop div > span,
.store-desc {
  font-size: 14px;
}
.storeImg {
  width: 100%;
  height: 25vh;
  object-fit: cover;
  margin-bottom: 5px;
}
</style>
