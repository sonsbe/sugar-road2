<template>
    <div>
        <Card v-for="review in reviewPage" :data = "review"></Card>
    </div>
</template>
<script setup>
    import {defineProps, onMounted, ref} from 'vue';
    import Card from '@/components/review/Card.vue';
    import {apiService} from '@/services/APIService.js';

    const props = defineProps( {
        reviewPageURL : String
    });
    let url = props.reviewPageURL;
    const reviewPage = ref([]);

    async function nextReview(url){
        let response = await apiService.get(url).catch(error => console.log(error))
        reviewPage.value = reviewPage.value.concat(response.data._embedded.reviewResponseVOList);
        console.log(reviewPage.value)
        return response.data._links.next != undefined?response.data._links.next.href:"";
    }
    
    onMounted(
        () => {
            url = nextReview(url);
        }
    )

</script>
<style scoped>
    @import "@/assets/review.css";
</style>