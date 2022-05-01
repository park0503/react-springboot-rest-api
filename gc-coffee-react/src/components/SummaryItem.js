import React from "react"

export function SummaryItem({ productName, counts }) {
    return (
        <div class="row">
            <h6 class="p-0">{productName} <span class="badge bg-dark text-">{counts}개</span></h6>
        </div>
    )
}