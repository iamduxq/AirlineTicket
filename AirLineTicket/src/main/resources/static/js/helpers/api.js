export async function apiGet(url) {
    const res = await fetch(url);
    if (!res.ok) {
        const text = await res.text();
        throw new Error(`API Get Error: ${res.status} - ${text}`);
    }

    return res.json();
}

export async function apiPost(url, data = {}) {
    const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
    });
    if (!res.ok) {
        const text = await res.text();
        throw new Error(`API Post Error: ${res.status} - ${text}`);
    }
    return res.json();
}

export async function apiPut(url, data ={}) {
    const res = await fetch(url, {
        method: "PUT",
        headers: { "Content-Type" : "application/json" },
        body: JSON.stringify(data)
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`API Put Error: ${res.status} - ${text}`);
    }
    return res.json();
}

export async function apiDelete(url) {
    const res = await fetch(url, {method: "DELETE"});
    if (!res.ok) {
        const text = await res.text();
        throw new Error(`API Delete Error: ${res.status} - ${text}`);
    }
    return res.json();
}