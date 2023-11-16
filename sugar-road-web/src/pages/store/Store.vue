<template>
  <div>Store page</div>
  <StoreCard
    class="container"
    v-for="store in storeList.value"
    :key="store.storeId"
    :storeData="{ storeName: store.storeName, address: store.address, phoneNumber:store.phoneNumber, storeDesc:store.storeDesc , storeImagePath: store.storeImagePath}"
  ></StoreCard>
</template>
<script setup>
import axios from "axios";
import StoreCard from "../../components/store/StoreCard.vue";
import { onMounted, reactive } from "vue";
let storeList = reactive([]);
onMounted(async () => {
  await getStoreList();
  console.log(storeList.value);
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
<style>
.container {
  border: 1px solid black;
}
</style>
