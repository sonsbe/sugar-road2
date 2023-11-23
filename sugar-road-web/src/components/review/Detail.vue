<template>
    <div class="store-like-back">
        <RouterLink :to="'/store/' + data.storeId">
            <button id="backBtn">◀</button>
        </RouterLink>
    </div>
    <table class="v-table pink1 left">
        <tr>
            <td class = "v-table-half t4 bold">
                🍰 {{ data.nickname }}
            </td>
            <td class = "v-table-quater">
            </td>
            <td class = "v-table-quater t6 bold right">
                {{ typeof data.postedDate === 'string'?data.postedDate.substring(0,10):""}}
            </td>
        </tr>
        <tr>
            <td colspan="3" class = "t5 v-item">
                {{ data.content }}
            </td>
        </tr>
        <tr>
            <td>
                <Suspense>
                    <Recommendation v-if="data._links!=undefined" :data = "data._links"></Recommendation>
                </Suspense>
            </td>
            <td class = "t5 t-lightgray right">
                <router-link :to="'/review/edit/' + data.id" v-if="data.userId === userId">수정</router-link>
            </td>
            <td class = "t5 center t-lightgray">
                <router-link @click = "deleteReview(data._links.self.href)" :to="'/store/' + data.storeId" v-if="data.userId === userId">삭제</router-link>
            </td>
        </tr>
        <tr>
            <td colspan="3" v-if = "data.reviewImagePath!=null">
                <img :src = "data.reviewImagePath"  class = "data-image">
            </td>
        </tr>
    </table>
</template>
<script setup>
    import {apiService} from '@/services/APIService.js';
    import Recommendation from '@/components/recommendation/Recommendation.vue';
    import {defineProps, provide, onMounted, ref} from 'vue';

    const props = defineProps( {
        data : Object
    });
    const userId = sessionStorage.getItem("user");
    function deleteReview(url){
        apiService.delete(url);
    }
</script>
<style scoped>
    @import "@/assets/review.css";
    @import "@/assets/storeDetail.css";

</style>