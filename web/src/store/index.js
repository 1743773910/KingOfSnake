import { createStore } from 'vuex'
import ModuleUser from "@/store/user"
import ModulePk from "@/store/pk"
import MoudleRecord from "@/store/record"

export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user : ModuleUser,
    pk : ModulePk,
    record : MoudleRecord
  }
})
