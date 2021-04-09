const productsList = document.getElementById('productsList')
const lowerPrice = document.getElementById('price-min-control');
const higherPrice = document.getElementById('price-max-control');
const updateResults = document.getElementById('updateResultsBtn');
const products = [];
let displayedProducts = [];

fetch("http://localhost:8080/api/products").
then(response => response.json()).
then(data => {
    for (let product of data) {
        products.push(product);
    }

    displayedProducts = [...products];
    showProducts(displayedProducts);
    priceRange(displayedProducts);
})

const showProducts = (prods) => {
    if(prods.length === 0) {
        productsList.innerHTML = "<h1>No results found.</h1>";
        return;
    }
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

function priceRange(prods) {
    var lowest = Number.POSITIVE_INFINITY;
    var highest = Number.NEGATIVE_INFINITY;
    var tmp;
    for (var i = prods.length - 1; i >= 0; i--) {
        tmp = prods[i].price;
        if (tmp < lowest) lowest = tmp;
        if (tmp > highest) highest = tmp;
    }

    lowerPrice.value = lowest;
    lowerPrice.setAttribute("min", lowest);

    higherPrice.value = highest;
    higherPrice.setAttribute("max", highest);
}

updateResults.onclick = function() {
    let lowPrice = parseInt(lowerPrice.value);
    let highPrice = parseInt(higherPrice.value);

    if(lowPrice > highPrice) {
        alert('The lower price can not be higher than the higher price.');
        return;
    }

    let sportsIds = [...document.querySelectorAll('.inpSports:checked')].map(e => e.value)
    if(sportsIds.length === 0) {
        alert('Select at least one sport.');
        return;
    }

    let types = [...document.querySelectorAll('.inpTypes:checked')].map(e => e.value)
    if(types.length === 0) {
        alert('Select at least one type.');
        return;
    }

    displayedProducts = products.filter(prod => {
        if(prod.price < lowPrice || prod.price > highPrice) return false;
        if(prod.sportId != null && sportsIds.indexOf(prod.sportId) < 0) return false;
        if(types.indexOf(prod.type) < 0) return false;

        return true;
    });
    showProducts(displayedProducts);
}

function productSort(type) {
    if(type === 1) {
        displayedProducts.sort((a, b) => (a.price > b.price) ? 1 : -1);
    } else if(type === 2) {
        displayedProducts.sort((a, b) => (a.price < b.price) ? 1 : -1);
    }

    showProducts(displayedProducts);
}