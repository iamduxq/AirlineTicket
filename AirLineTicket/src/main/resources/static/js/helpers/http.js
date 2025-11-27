export async function apiRequest(url, method ="GET", body = null) {
    const options = {
        method,
        headers: {"Content-Type": "application/json"}
    };
    if (body) options.body = JSON.stringify(body);
    const res = await fetch(url, options);
    if (!res.ok){
        const text = await res.text();
        throw new Error(text || "Request thất bại");
    }

    if (res.status !== 204) {
        return res.json();
    }
}