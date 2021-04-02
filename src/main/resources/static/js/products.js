const productsList = document.getElementById('productsList')
const products = [];

fetch("http://localhost:8080/api/products").
then(response => response.json()).
then(data => {
    for (let product of data) {
        products.push(product);
    }
    showProducts(products);
})

const showProducts = (prods) => {
    productsList.innerHTML = prods.map(function (a) {
        if(a.imageUrl == null) a.imageUrl = "https://via.placeholder.com/240x240/5fa9f8/efefef"
        return `<div class="col-5 col-md-5 col-lg-3 mb-3">
                <div class="card h-100 border-0">
                    <div class="card-img-top">
                        <img src = "${a.imageUrl}" 
                            class="img-fluid mx-auto d-block" alt="Thumbnail" data-holder-rendered="true"
                     style="height: 240px; width: 240px;"/>
                    </div>
                    <div class="card-body text-center">
                        <h4 class="card-title">
                            <a href="/products/details/${a.id}" class=" font-weight-bold text-dark text-uppercase small">
                                ${a.name}
                            </a>
                        </h4>
                        <h5 class="card-price text-warning">
                            <i>&#36;${a.price}</i>
                        </h5>
                    </div>
                </div>
            </div>`
    }).join('');
}