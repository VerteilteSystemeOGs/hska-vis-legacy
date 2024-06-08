import http from 'k6/http';
import { sleep, check } from 'k6';
import { Counter, Trend } from 'k6/metrics';
import { htmlReport } from "https://raw.githubusercontent.com/benc-uk/k6-reporter/main/dist/bundle.js";
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.0.1/index.js';
import faker from 'https://cdnjs.cloudflare.com/ajax/libs/Faker/3.1.0/faker.min.js';

const minikubeIp = __ENV.MINIKUBE_IP;

const userPods = __ENV.USER_PODS.split('\n').map((userPod) => userPod.replaceAll('-', '_'));

export const options = {
  scenarios: {
    getAllCategories: {
      executor: 'constant-arrival-rate',
      exec: 'getAllCategories',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 10,
      tags: {query: "getAllCategories", name: "getAllCategories"},
    },
    getAllProducts : {
      executor: 'constant-arrival-rate',
      exec: 'getAllProducts',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 10,
      tags: {query: "getAllProducts", name: "getAllProducts"},
    },
    createGetAndDeleteCategory : {
      executor: 'constant-arrival-rate',
      exec: 'createGetAndDeleteCategory',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 5,
      tags: {query: "createGetAndDeleteCategory", name: "createGetAndDeleteCategory"},
    },
    createGetAndDeleteProduct : {
      executor: 'constant-arrival-rate',
      exec: 'createGetAndDeleteProduct',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 5,
      tags: {query: "createGetAndDeleteProduct", name: "createGetAndDeleteProduct"},
    },
    createGetAndDeleteUser : {
      executor: 'constant-arrival-rate',
      exec: 'createGetAndDeleteUser',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 3,
      tags: {query: "createGetAndDeleteUser", name: "createGetAndDeleteUser"},
    },
    checkPod: {
      executor: 'constant-arrival-rate',
      exec: 'getPodName',
      duration: "20s",
      preAllocatedVus: 10,
      rate: 5,
      tags: {query: "getPodName", name: "getPodName"},
    },
  }
};

const trends = {
  category: {
    service: new Trend('Category_Service', true),
    getAll: new Trend('Category_getAll', true),
    createNew: new Trend('Category_createNew', true),
    get: new Trend('Category_get', true),
    delete: new Trend('Category_delete', true),
  },
  product: {
    service: new Trend('Product_Service', true),
    getAll: new Trend('Product_getAll', true),
    createNew: new Trend('Product_createNew', true),
    getDetails: new Trend('Product_getDetails', true),
    delete: new Trend('Product_delete', true),
  },
  user: {
    service: new Trend('User_Service', true),
    createNew: new Trend('User_createNew', true),
    get: new Trend('User_get', true),
    delete: new Trend('User_delete', true),
    pods: Object.fromEntries(userPods.map((userPod) => [userPod, new Counter(`User_pod_${userPod}`)])),
  }
}

export function setup() {
  const res = http.post(`http://${minikubeIp}/category/v1/categories`, JSON.stringify({categoryName: "TestKategorie"}), {headers: {"Content-Type": "application/json"}});
  
  if (res.status !== 200) {
    throw `Failure during setup: ${res}`;
  }

  return {defaultCategoryId: res.json().id};
}

export function teardown(data) {
  const category = http.del(`http://${minikubeIp}/category/v1/categories/${data.defaultCategoryId}`);
  
  check(category, {
    'is status 200': (r) => r.status === 200,
  });
}

export function getAllCategories() {
  const res = http.get(`http://${minikubeIp}/category/v1/categories`, {
    tags: {name: "getAllCategories"}
  });
  
  check(res, {
    'is status 200': (r) => r.status === 200,
  });

  trends.category.service.add(res.timings.duration);
  trends.category.getAll.add(res.timings.duration);
}

export function getAllProducts() {
  const res = http.get(`http://${minikubeIp}/product/v1/products`, {
    tags: {query: "getAllProducts", name: "getAllProducts"}
  });
  
  check(res, {
    'is status 200': (r) => r.status === 200,
  });
  
  trends.product.service.add(res.timings.duration);
  trends.product.getAll.add(res.timings.duration);
}

export function createGetAndDeleteCategory(data) {
  const newCategory = {
    categoryName: 'testCategory',
  };

  const resCreate = http.post(`http://${minikubeIp}/category/v1/category`, JSON.stringify(newCategory), {
    headers: {"Content-Type": "application/json"},
    tags: {query: "createNewCategory", name: "createNewCategory"}
  });

  check(resCreate, {
    'is status 200': (r) => r.status === 200,
  });

  const categoryId = resCreate.json().id;

  trends.category.service.add(resCreate.timings.duration);
  trends.category.createNew.add(resCreate.timings.duration);

  const resGet = http.get(`http://${minikubeIp}/category/v1/categories/${categoryId}`);

  check(resGet, {
    'is status 200': (r) => r.status === 200,
  });

  trends.category.service.add(resGet.timings.duration);
  trends.category.get.add(resGet.timings.duration);

  const resDelete = http.del(`http://${minikubeIp}/category/v1/categories/${categoryId}`);

  check(resDelete, {
    'is status 204': (r) => r.status === 204,
  });

  trends.category.service.add(resDelete.timings.duration);
  trends.category.delete.add(resDelete.timings.duration);
}

export function createGetAndDeleteProduct(data) {
  const newProduct = {
    productName: 'testProduct',
    categoryId: data.defaultCategoryId,
    price: 100,
    details: "very nice",
  };

  const resCreate = http.post(`http://${minikubeIp}/product/v1/products`, JSON.stringify(newProduct), {
    headers: {"Content-Type": "application/json"},
    tags: {query: "createNewProduct", name: "createNewProduct"}
  });

  check(resCreate, {
    'is status 200': (r) => r.status === 200,
  });

  const productId = resCreate.json().id;

  trends.product.service.add(resCreate.timings.duration);
  trends.product.createNew.add(resCreate.timings.duration);

  const resDetails = http.get(`http://${minikubeIp}/product/v1/products/details/${productId}`);

  check(resDetails, {
    'is status 200': (r) => r.status === 200,
  });

  trends.product.service.add(resDetails.timings.duration);
  trends.product.getDetails.add(resDetails.timings.duration);

  const resDelete = http.del(`http://${minikubeIp}/product/v1/products/${productId}`);

  check(resDelete, {
    'is status 204': (r) => r.status === 204,
  });

  trends.product.service.add(resDelete.timings.duration);
  trends.product.delete.add(resDelete.timings.duration);
}

export function createGetAndDeleteUser() {
  const newUser = {
    userName: faker.internet.userName(),
    firstName: faker.name.firstName(),
    lastName: faker.name.lastName(),
    password: faker.internet.password(),
  };

  const resCreate = http.post(`http://${minikubeIp}/user/v1/users`, JSON.stringify(newUser), {
    headers: {"Content-Type": "application/json"},
    tags: {query: "createNewUser", name: "createNewUser"}
  });

  check(resCreate, {
    'is status 200': (r) => r.status === 200,
  });

  const userId = resCreate.json().id;

  trends.user.service.add(resCreate.timings.duration);
  trends.user.createNew.add(resCreate.timings.duration);

  const resGet = http.get(`http://${minikubeIp}/user/v1/users/${userId}`);

  check(resGet, {
    'is status 200': (r) => r.status === 200,
  });

  trends.user.service.add(resGet.timings.duration);
  trends.user.get.add(resGet.timings.duration);

  const resDelete = http.del(`http://${minikubeIp}/user/v1/users/${userId}`);

  check(resDelete, {
    'is status 204': (r) => r.status === 204,
  });

  trends.user.service.add(resDelete.timings.duration);
  trends.user.delete.add(resDelete.timings.duration);
}

export function getPodName() {
  const res = http.get(`http://${minikubeIp}/user/v1/pod/`);

  check(res, {
    'is status 200': (r) => r.status === 200,
  });

  const hostname = res.json().hostname.replaceAll('-', '_');

  trends.user.service.add(res.timings.duration);
  trends.user.pods[hostname].add(1);
}

//export const handleSummary = function (data) {
//  return {
//    'stdout': textSummary(data, { indent: ' ', enableColors: true }), // Show the text summary to stdout...
//    "results.html": htmlReport(data, { title: new Date().toLocaleString() }),
//    'summary.json': JSON.stringify(data), // and a JSON with all the details...
//  }
//}
