<template>
  <div>가게 등록 페이지</div>
  <form @submit.prevent="createStoreTest">
    가게명 <input type="text" v-model="storeName" /> <br />
    가게번호 <input type="text" v-model="phoneNumber" /> <br />
    가게주소 <input type="text" v-model="address" /> <br />
    대표 이미지 <input type="file" /> <br />
    설명 <input type="text" v-model="storeDesc" />
    <h3>메뉴</h3>
    메뉴이름 <input type="text" v-model="menuName" /> <br />
    메뉴이미지<input type="file" name="menuImages" />
    <button @Click="createStoreTest">테스트 버튼</button>
    <input type="submit" value="저장" />
    <input type="reset" value="취소" />
  </form>
</template>

<script setup>
function createStoreTest() {
  console.log("storeName");
  console.log(storeName);
}
</script>

<style scoped></style>
