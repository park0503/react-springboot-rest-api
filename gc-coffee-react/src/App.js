import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css'
import React, { useEffect, useState } from 'react';
import { ProductList } from './components/ProductList';
import { Summary } from './components/Summary';
import axios from 'axios';

function App() {
    const [products, setProducts] = useState([
    ]);
    const [items, setItems] = useState([
    ]);
    const handleAddClicked = id => {
        const product = products.find(v => v.productId === id);
        const found = items.find(v => v.productId === id);
        const updatedItems = found ? items.map(v => v.productId === id ? { ...v, counts: v.counts + 1 } : v) : [...items, { ...product, counts: 1 }];
        setItems(updatedItems);
        console.log(products.find(v => v.productId === id), "added!");
    };
    useEffect(() => {
        axios.get('http://localhost:8080/api/v1/products')
            .then(v => setProducts(v.data));
    }, []);

    const handleOrderSubmit = (order) => {
        if (items.length === 0) {
            alert("상품을 추가해 주세요.");
        } else {
            axios.post("http://localhost:8080/api/v1/orders", {
                email: order.email,
                address: order.address,
                postcode: order.postcode,
                orderItems: items.map(i => ({
                    productId: i.productId,
                    category: i.category,
                    price: i.price,
                    quantity: i.counts
                }))
            }).then(v => alert("주문이 정상적으로 접수되었습니다."), e => {
                alert("서버 장애");
                console.error(e);
            });
        }
        console.log(order, items);
    }
    return (
        <div className="App">
            <div class="container-fluid">
                <div class="row justify-content-center m-4">
                    <h1 class="text-center">Grids & Circle</h1>
                </div>
                <div class="card">
                    <div class="row">
                        <div class="col-md-8 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                            <ProductList products={products} onAddClick={handleAddClicked} />
                        </div>
                        <div class="col-md-4 summary p-4">
                            <Summary items={items} onOrderSubmit={handleOrderSubmit} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default App;
