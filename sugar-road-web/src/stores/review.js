import { defineStore } from 'pinia'
import axios from 'axios';
export const useReview = defineStore("review", {
    state: () => ({
        reviews: [],
    }),
    getters: {
        getReviews(state){
            return state.reviews
        },
        getReviewsOfStore(state){
            return (storeId) => state.reviews.filter((review) => review.storeId === storeId);
        },
        getReviewById(id){
            return (storeId) => state.reviews.filter((review) => review.id === id);
        }
    },
    actions: {
        async fetchReviews(api) {
            try {
                    const data = await axios.get(api);
                    this.reviews = this.reviews.concat(
                        data._embedded.reviewResponseVOList
                    );
                    return data._links.next.href;
                }
            catch (error) {
                alert(error)
                console.log(error)
            }
        }
    },
})