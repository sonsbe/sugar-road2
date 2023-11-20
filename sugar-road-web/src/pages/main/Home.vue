<template>
  <div class="title">
    <img src="images/logonoback.png" class="main-title" />
  </div>
  <span><b>인기 급상승 게시글🧁</b></span><br/>
  <div class="swiper">
    <div class="swiper-wrapper">
      <Swiper
        :slides-per-view="2.3"
        :space-between="10"
        :centered-slides="true"
        :autoplay="{
          delay: 2500,
          disableOnInteraction: false
        }"
        :loop="true"
        :loop-additional-slides="1"
        @swiper="onSwiper"
        @slideChange="onSlideChange"
      >
        <SwiperSlide
          v-for="(post, index) in resultList.postList"
        >
          <HomeCard :object="post" :key="index"></HomeCard>
        </SwiperSlide>
      </Swiper>
    </div>
  </div>
  <div class="text-box" id="text-second-box">
    <h4>Recent Dessert</h4>
    i love sugar you too? hahaha
  </div>

</template>
<script setup>
import { Swiper, SwiperSlide } from "swiper/vue";
import HomeCard from "../../components/home/HomeCard.vue";
import { onMounted, ref } from "vue";
import { api } from "@/common";
const resultList = ref({});
const onSwiper = (swiper) => {
  console.log(swiper);
};
const onSlideChange = () => {
  console.log("slide change");
};
onMounted(() => {
  api("http://localhost:1023/home", "GET", {}).then((response) => {
    console.log(response);
    resultList.value = response;
  });
});
</script>

<style>

@import "https://cdn.jsdelivr.net/npm/swiper@10/swiper-bundle.min.css";
.title {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150px;
}
.main-title {
  height: 100px;
}
span > b {
  margin: 10px;
}

.text-box {
  height: 10vh;
  background-color: #fbe0e5;
  color: #ffffff;
  text-align: center;
}
.text-box > h4 {
  padding: 3vh 10px 5px;
  color: #ffffff;
}
.swiper {
  width: 96%;
  height: 25vh;
  padding: 10px;
  background-color: #fefefe;
  margin-top: 5px;
}
.swiper-slide {
  width: 28vw;
  height: 20vh;
}
.storeImg {
  width: 100%;
  height: 100%;
}
.swiper-slide > a {
  text-decoration: none;
}
.store-like {
  font-size: 12px;
  text-align: right;
}
#text-second-box {
  background-color: #c3dcf1;
  margin-top: 4vh;
}
</style>
