import React from 'react'
import "./Table.scss"
export const TableContainer = ({ data }) => {
    return (
        <table className="MyTable">
            <tbody>
                <>
                    {data?.map((row, i) => {
                        return (
                            <tr key={i}>
                                {row?.map((value, j) => {
                                    return (
                                        <td
                                            key={j}
                                            className="grid-box"
                                            style={{
                                                backgroundColor:
                                                    value === 1 ? "#2ecc71" : "#f1f2f6",
                                            }}>
                                            {value}
                                        </td>
                                    );
                                })}
                            </tr>
                        );
                    })}
                </>
            </tbody>
        </table>
    )
}
