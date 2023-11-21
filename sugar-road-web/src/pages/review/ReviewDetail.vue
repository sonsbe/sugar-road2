<template>
    <Detail :data="data"></Detail>
    <CommentCards
        :commentPageURL = "'http://localhost:1023/review-comment/of/review/' + reviewId"
        commentType = "review"
        :referenceId = "reviewId"
    />
</template>
<script setup>
    import Detail from '@/components/review/Detail.vue';
    import CommentCards from '@/components/comment/CommentCards.vue';
    import { onMounted, ref } from 'vue';
    import { useRoute } from "vue-router";
    import { apiService } from '../../services/APIService';
    const currentRoute = useRoute();
    const reviewId = currentRoute.params.reviewId;
    const data = ref({});
    onMounted(
        async () => {
            const response = await apiService.get(`http://localhost:1023/review/${reviewId}`);
            data.value = response.data
        }
    )
</script>
<style scoped>
    @import "@/assets/comment.css";
    @import "@/assets/ui.css";
</style>