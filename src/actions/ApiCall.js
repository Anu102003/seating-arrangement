import axios from 'axios';

const API_BASE_URL = 'http://localhost:8090/api/v1';
// const API_BASE_URL = 'http://192.168.1.220:8081/api/v1';

const baseApi = axios.create({
    baseURL: API_BASE_URL,
});
const api = axios.create({
    baseURL: API_BASE_URL,
});

const accessToken = localStorage.getItem('accessToken');
if (accessToken) {
    api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
}

api.interceptors.request.use(
    (config) => {
        const accessToken = localStorage.getItem('accessToken');
        if (accessToken) {
            config.headers.Authorization = `Bearer ${accessToken}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// export const loginApi = async (data) => {
//     try {
//         const response = await baseApi.post(`/login`, data);
//         const accessToken=response.data.data.accessToken
//         localStorage.setItem('accessToken', accessToken);
//         api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
//         console.log(accessToken);
//         return response.data;
//     }
//      catch (error) {
//         console.log('Failed to login layout', error)
//         return error;
//     }
// };
export const loginApi = (data) => {
    return baseApi.post(`/login`, data)
        .then(response => {
            const accessToken = response.data.data.accessToken;
            localStorage.setItem('accessToken', accessToken);
            api.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
            console.log(accessToken);
            return response.data;
        })
        .catch(error => {
            console.log('Failed to login layout', error);
            return error; 
        });
};
export const registerApi = async (data) => {
    try {
        const response = await baseApi.post(`/sign_up`, data);
        return response.data;
    } catch (error) {
        // console.log('Failed to register layout', error)
        return error;
    }
};

export const logoutApi = async () => {
    try {
        const accessToken = localStorage.getItem('accessToken');
        console.log(accessToken)
        const response = await baseApi.post(`/logout`,{
            accessToken:accessToken
        });
        const responseData = response;
        console.log(responseData)
        return responseData;
    } catch (error) {
        console.log('Failed to register layout', error)
    }
};


export const createLayoutApi = async (name, layout) => {
    console.log(name, layout)
    try {
        const response = await api.post(`/company`,
            {
                "companyName": name,
                "companyLayoutList": layout
            }
        );
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        console.log('Failed to create layout', error)
    }
};
export const getAlllayoutApi = async (name) => {
    try {
        const response = await api.get(`/company/${name}`);
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        console.log('Failed to get all layout', error)
    }
};

export const allocationApi = async (data) => {
    try {
        const response = await api.post("/allocation", data);
        console.log(response)
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        console.log('Failed to get allocation', error)
    }
};

export const csvFileApi = async (file, setInCorrectFile) => {
    try {
        const response = await api.post("/file", file,
            {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
        setInCorrectFile(false)
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        setInCorrectFile(true)
        console.log('Failed to get allocation', error)
    }
};

export const addLayoutApi = async (name, layout) => {
    console.log(layout)
    try {
        const response = await api.post(`/layout`,
            {
                "companyName": name,
                "defaultLayout": layout
            }
        );
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        console.log('Failed to add layout', error)
    }
};

export const updateLayoutApi = async (id, name, layout) => {
    console.log(layout)
    try {
        const response = await api.post(`/layout`,
            {
                "layoutId": id,
                "companyName": name,
                "defaultLayout": layout
            }
        );
        const layoutData = response.data;
        console.log(layoutData)
        return layoutData;
    } catch (error) {
        console.log('Failed to update layout', error)
    }
};

export const deleteLayoutApi = async (id, name) => {
    console.log(id, name)
    try {
        const response = await api.post(`/layout`,
            {
                "layoutId": id,
                "companyName": name
            }
        );
        const layoutData = response.data;
        console.log(layoutData)
        return layoutData;
    } catch (error) {
        console.log('Failed to delete layout', error)
    }
};

export const getAllAllocationApi = async (id) => {
    try {
        const response = await api.get(`/layout/${id}`);
        const layoutData = response.data;
        return layoutData;
    } catch (error) {
        console.log('Failed to get all allocation', error)
    }
};