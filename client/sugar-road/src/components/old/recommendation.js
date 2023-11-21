class Recommendation extends HTMLButtonElement {
    constructor(){
        super();
        this.referenceType = this.getAttribute("data-referenceType");
        this.referenceId = this.getAttribute("data-referenceId");
        this.classList.add("t4");
        this.classList.add("bold");

        this.render();
        this.addEventListener("click", this.recommend);
    }
    render(){
        fetch(`/recommendation/check?referenceType=${this.referenceType}&referenceId=${this.referenceId}`)
          .then(response => {return response.json();})
          .then(json => this.readJson(json));
    }
    recommend(){
        let api = this.check?"delete":"write";
        fetch(`/recommendation/${api}?referenceType=${this.referenceType}&referenceId=${this.referenceId}`)
          .then(response => {return response.json();})
          .then(json => this.readJson(json));
    }
    readJson(json){
      this.textContent = "♥ " + json['count'];
      this.check = json['check'];
      this.mark();
    }
    mark(){
      if (this.check === true){
          this.style.color = 'var(--pink3)';
      }
      else {
          this.style.color = 'var(--lightgray)';
      }
    }
  }

customElements.define("custom-recommendation", Recommendation, { extends: "button" });
//<button is = "custom-recommendation" data-userId="추천테스트용" data-referenceType="R" th:data-referenceId="${reviewDTO.reviewId}"></button>
//<script src="/js/recommendation.js"></script>
// export default Recommendation;