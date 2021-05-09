import { getCurrentUserUrl, getUserWithIdUrl } from './urls';
import axios from 'axios';
import authHeader from '../auth/methods';

export const getCurrentUser = async () => {
  console.log(getCurrentUserUrl());
  try {
    const response = await axios.get(getCurrentUserUrl(), { headers: authHeader() });
    return {
      userName: response.data[0]?.login,
      photoUrl: response.data[0]?.profilePhoto,
    };
  } catch (error) {
    console.log(error);
    return {};
  }
};

export const getUserWithId = async (id) => {
  try {
    const response = await axios.get(getUserWithIdUrl(id), { headers: authHeader() });
    return response.data;
  } catch (error) {
    console.log(error);
    return null;
  }
};
