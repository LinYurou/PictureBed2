<template>
  <div class="JNPF-common-layout">
    <div class="JNPF-common-layout-center">
      <search :parentQuery.sync="query"
              :parentListQuery.sync="listQuery"
              @initData="initData"
              @reset="reset"
			  />
      <div class="JNPF-common-layout-main JNPF-flex-main">
        <Header box="MstList"
                box-type="List"
                v-show="!formVisible"
                has-form
                has-form-edit
                has-form-view
                :parentListQuery.sync="listQuery.MstList"
                :total="total.MstList"
                @initData="initData"
                @newForm="newFormHandle"
                @save="saveHandle"
                @delete="deleteHandle"
                @editForm="formHandle"
                @viewForm="formHandle"
                @exportData="exportData"
                @reset="reset">
          <ColumnSettings v-model="showColumns.MstList" :data="columnList.MstList" is-button/>
        </Header>
        <MstList ref="MstList"
                 v-show="!formVisible"
                 :parentDataForm.sync="dataForm"
                 :apiUrl="apiUrl.mst"
                 :show-columns="showColumns.MstList"
                 @refreshDtl="handleRefreshDtl"
                 @refresh="refresh"
                 @init="initData"/>

        <transition name="el-zoom-in-center">
          <div class="ialice-form"
               v-show="formVisible">
            <el-tabs v-model="activePanel"
                     type="border-card"
                     v-show="formVisible">
              <el-tab-pane label="主表標題"
                           name="MstPanel">
                <Header box="MstForm"
                        box-type="Form"
                        has-form
                        show-go-back
                        :parentListQuery.sync="listQuery.MstList"
                        :total="total.MstList"
                        @initData="initData"
                        @newForm="newFormHandle"
                        @save="saveHandle"
                        @delete="deleteHandle"
                        @exportData="exportData('MstList')"
                        @reset="reset"
                        @goBack="goBack">
                </Header>
                <MstForm ref="MstForm"
                         :parentDataForm="dataForm"
                         :api-url="apiUrl.mst"
                         @refresh="refresh"
                         @refreshDtl="handleRefreshDtl"
                         @init="initData"
						 :payTypeOptions="payTypeOptions"
						 :countryTypeOptions="countryTypeOptions"
						 />
              </el-tab-pane>

              <el-tab-pane label="標題1"
                           name="DtlPanel"
						   :class="showPaneClass"
						   >
                <Header box="DtlList"
                        box-type="List"
                        :parentListQuery.sync="listQuery.DtlList"
                        :total="total.DtlList"
                        @initData="initData"
                        @newRow="newRowHandle"
                        @save="saveHandle"
                        @delete="deleteHandle"
                        @exportData="exportData"
                        @reset="reset">
                  <ColumnSettings v-model="showColumns.DtlList" :data="columnList.DtlList" is-button/>
                </Header>
                <DtlList ref="DtlList"
                         :parentDataForm="dataForm"
                         :api-url="apiUrl.dtl"
                         :show-columns="showColumns.DtlList"
                         @refresh="refresh"
                         @init="initData"/>
              </el-tab-pane>
				<el-tab-pane label="標題2"
                         name="Dtl2Panel"
						 :class="showPaneClass"
						 >
              <Header box="Dtl2List"
                      box-type="List"
                      :parentListQuery.sync="listQuery"
                      :total="total.Dtl2List"
                      @initData="initData"
                      @newRow="newRowHandle"
                      @save="saveHandle"
                      @delete="deleteHandle"
                      @exportData="exportData"
                      @reset="reset">
				<ColumnSettings v-model="showColumns.Dtl2List" :data="columnList.Dtl2List" is-button/>	  
              </Header>
              <div class="pane-scroll">
                <Dtl2List ref="Dtl2List"
                         :parentDataForm="dataForm"
                         :api-url="apiUrl.dtl2"
						 :show-columns="showColumns.Dtl2List"
                         @refresh="refresh"
                         @init="initData"
			  :guestCode1typeOptions="guestCode1typeOptions"
						 />
              </div>
            </el-tab-pane>

				<el-tab-pane label="標題3"
                         name="Dtl3Panel"
						 :class="showPaneClass"
						 >
              <Header box="Dtl3List"
                      box-type="List"
                      :parentListQuery.sync="listQuery"
                      :total="total.Dtl3List"
                      @initData="initData"
                      @newRow="newRowHandle"
                      @save="saveHandle"
                      @delete="deleteHandle"
                      @exportData="exportData"
                      @reset="reset">
				<ColumnSettings v-model="showColumns.Dtl3List" :data="columnList.Dtl3List" is-button/>	  
              </Header>
              <div class="pane-scroll">
                <Dtl3List ref="Dtl3List"
                         :parentDataForm="dataForm"
                         :api-url="apiUrl.dtl3"
						 :show-columns="showColumns.Dtl3List"
                         @refresh="refresh"
                         @init="initData"
						 />
              </div>
            </el-tab-pane>


			  
            </el-tabs>
          </div>
        </transition>
        <ExportBox v-if="exportBoxVisible"
                   ref="ExportBox"
                   @download="download"/>
      </div>
    </div>
  </div>
</template>

<script>
import Search from "./Search";
import Header from "@/components/iAlice/PageCommon/Header";
import DtlList from "./DtlList";
import Dtl2List from "./Dtl2List";
import Dtl3List from "./Dtl3List";
import MstForm from "./MstForm";
import MstList from "./MstList";
import ExportBox from "@/components/iAlice/PageCommon/ExportBox"
import mixin from './js/index'

export default {
  mixins: [mixin],
  name: "md5",
  components: {
  MstList, 
  MstForm, 
  DtlList, 
  Dtl2List, 
  Dtl3List, 
  Header, 
  Search, 
  ExportBox
  },
}
</script>

<style scoped lang="scss">
  .ialice-form {
    overflow-y: auto;
  }

  .pane-scroll {
    overflow: auto;

    &:after {
      content: "";
      display: block;
      clear: both;
    }
  }
  .showPane{
    display: block;
  }
  .disablePane{
    display: none;
  }

</style>
