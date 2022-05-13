import React, { useState } from "react";
import { SummaryItem } from "./SummaryItem";

export function Summary({ items = [], onOrderSubmit }) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.counts), 0);
    const [order, setOrder] = useState({
        email: "", address: "", postcode: ""
    });
    const handleEmailInput = (e) => {
        setOrder({
            ...order, email: e.target.value
        });
    }
    const handleAddressInput = (e) => {
        setOrder({
            ...order, address: e.target.value
        });
    }
    const handlePostcodeInput = (e) => {
        setOrder({
            ...order, postcode: e.target.value
        });
    }
    const handleSubmit = (e) => {
        if (order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력값을 확인해주세요!")
        } else {
            onOrderSubmit(order);
        }
        console.log(order);
    }
    return (
        <React.Fragment>
            <div>
                <h5 class="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr />
            {items.map(v => <SummaryItem key={v.id} counts={v.counts} productName={v.productName} />)}
            <form>
                <div class="mb-3">
                    <label for="email" class="form-label">이메일</label>
                    <input type="email" class="form-control mb-1" id="email" value={order.email} onChange={handleEmailInput} />
                </div>
                <div class="mb-3">
                    <label for="address" class="form-label">주소</label>
                    <input type="text" class="form-control mb-1" id="address" value={order.address} onChange={handleAddressInput} />
                </div>
                <div class="mb-3">
                    <label for="postcode" class="form-label">우편번호</label>
                    <input type="text" class="form-control" id="postcode" value={order.postcode} onChange={handlePostcodeInput} />
                </div>
                <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
            </form>
            <div class="row pt-2 pb-2 border-top">
                <h5 class="col">총금액</h5>
                <h5 class="col text-end">{totalPrice}원</h5>
            </div>
            <button class="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
        </React.Fragment>
    )
}