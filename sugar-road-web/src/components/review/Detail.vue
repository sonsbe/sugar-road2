<template>
    <a href="javascript:history.back();" id="backBtn" style="text-decoration:none">◀</a><br>
    <div class ="center"><h3>자세히 보기</h3></div>
    <table class="v-table pink1">
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
                    <Recommendation :data = "data._links"></Recommendation>
                </Suspense>
            </td>
            <td class = "t5 t-lightgray right">
                <router-link :to="'/review/edit/' + data.id">수정</router-link>
            </td>
            <td class = "t5 center t-lightgray">
                <router-link @click = "deleteReview(data._links.self.href)" to="/store">삭제</router-link>
            </td>
        </tr>
        <tr>
            <td colspan="3" v-if = "data.reviewImagePath!=null">
                <img :src = "data.reviewImagePath"  class = "data-image">
            </td>
        </tr>
    </table>
    <!-- Comment Input-->
    <!-- Comment -->
</template>
<script setup>
    import {apiService} from '@/services/APIService.js';
    import Recommendation from '@/components/recommendation/Recommendation.vue';
    import {defineProps, provide, onMounted, ref} from 'vue';

    const props = defineProps( {
        data : Object
    });

    function deleteReview(url){
        apiService.delete(url);
    }
</script>
<style scoped>
    @import "@/assets/review.css";
</style>