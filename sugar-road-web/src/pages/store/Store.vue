<template>
  <div>Store page</div>
  <router-link :to="'/store/write'">
    <button @onClick="createStore">가게 등록</button>
  </router-link>
  <StoreCard
    class="container"
    v-for="store in storeList.value"
    :key="store.storeId"
    :storeData="{
      storeName: store.storeName,
      address: store.address,
      phoneNumber: store.phoneNumber,
      storeDesc: store.storeDesc,
      storeImagePath: store.storeImagePath,
      storeId: store.storeId,
    }"
  >
  </StoreCard>
</template>
<script setup>
import axios from "axios";
import StoreCard from "../../components/store/StoreCard.vue";
import { onMounted, reactive } from "vue";
let storeList = reactive([]);
onMounted(async () => {
  await getStoreList();
  // console.log(storeList.value);
});
async function getStoreList() {
  try {
    const response = await axios.get("http://localhost:1023/store");
    storeList.value = response.data;
    console.log(storeList.value);
  } catch (err) {
    console.log(err);
  }
}
</script>
<style scoped>
.container {
  border: 1px solid black;
}
</style>
