class Review extends HTMLElement {
    constructor(){
        super();
        this.storeId = this.getAttribute("data-storeId");
        console.log(`this.storeId : ${this.storeId}`);

        this.render();
    }
    render(){
        fetch(`/review?storeId=${this.storeId}`)
          .then(response => {return response.json();})
          .then(json => this.readJson(json));
    }
    readJson(json){
      Object.values(json).forEach((r) => {
          console.log(r);
          this.innerHTML += `

            <div class = "v-item pink1" onclick="location.href = '/review/detail?id=${r["reviewId"]}'">
                <table class = "v-table">
                    <tr>
                        <td><h5>🍰 ${r["nickname"]}</h5></td>
                        <td class = "v-table-quater right t6 bold">${r["postedDate"].substring(0,10)}</td>
                    </tr>
                    <tr>
                      <td colspan="2" class = "t5"><h6>${"⭐".repeat(r["star"])}</h6></td>
                    </tr>
                    <tr>
                        <td colspan="2"class = "t5">
                          ${r["content"]}
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class = "review-image-box">
                          <img src = '${r["reviewImagePath"]}' class = "review-image" style=${r["reviewImagePath"]==undefined?"display:none":""}>
                        </td>
                    </tr>
                    <tr>
                        <td>
                          <button is = "custom-recommendation" class = "review-button" data-referenceType="R" data-referenceId=${r["reviewId"]}></button>
                        </td>
                    </tr>
                </table>
            </div>

          `;
      });
    }
  }
//.substring(0, 10)
customElements.define("custom-review", Review);

//<custom-review th:data-storeId=${storeDTO.storeId}></custom-review>
//<script src="/js/review.js"></script>

// export default Recommendation;