<template>
    <Edit :data="data"></Edit>
</template>
<script setup>
    import Edit from '@/components/review/Edit.vue';
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