<template>
    <a href="javascript:history.back();" id="backBtn" style="text-decoration:none">◀</a><br>
    <div class = "center t4 bold">리뷰 작성<span class = "t-lightgray t5">review</span></div>
        <table class="v-table">
        <tr>
            <td class="v-table-half">
            <div class="rating-box t5 center">
                <h5 class="t-lightgray" id="is-rating">당신의 만족도는?</h5>
            </div>
            </td>
            <td>
            <div class="stars t4 center">
                <i v-for="i in 5" :class="[stars[i]?'active':'', 'fa-solid', 'fa-star']" @click="activeStar(i)"></i>
            </div>
            </td>
        </tr>
        <tr>
            <td colspan = "2" class = "center">
                <textarea name ="content" class = "review-content-box t5" v-model="data.content" required></textarea>
            </td>
        </tr>
        <tr>
            <td colspan = "2">
                <input type="file" name="uploadImage">
            </td>
        </tr>
        <tr>
            <td colspan = "2" class = "center">
                <router-link :to="'/store/' + data.storeId">
                    <input type = "button" value = "작성" class = "pink2 v-item t6 bold no-border" @click="postReview('http://localhost:1023/review')">
                </router-link>
            </td>
        </tr>
        <tr>
            <td colspan = "2" class = "center">
                <router-link :to="'/store/' + data.storeId">
                    <input type = "button" value = "취소" class = "v-item t6 bold no-border lightgray">
                </router-link>
            </td>
        </tr>
        </table>
</template>
    
<script setup>
    import {ref} from 'vue';
    import {apiService} from '@/services/APIService.js';    
    import {defineProps} from 'vue';

    const props = defineProps( {
        data : Object
    });
    let starScore = 0;

    const stars = ref([false, false, false, false, false, false]);

    function activeStar(i){
        starScore = i;
        for (let j = 1; j < 6; j++ ) {
            if (j <= i){
                stars.value[j] = true;
            }
            else {
                stars.value[j] = false;
            }
        }
    }

    function postReview(url){
        let now = new Date();
        apiService.post(url, {
            storeId : props.data.storeId,
            postedDate : now.toISOString(),
            star : starScore,
            content : props.data.content,
            reviewImagePath : props.data.reviewImagePath
        }).catch(error => console.log(error));
    }
</script>
<style scoped>
    @import "@/assets/review.css";
</style>