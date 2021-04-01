import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { DGoodsComponent } from './list/d-goods.component';
import { DGoodsDetailComponent } from './detail/d-goods-detail.component';
import { DGoodsUpdateComponent } from './update/d-goods-update.component';
import { DGoodsDeleteDialogComponent } from './delete/d-goods-delete-dialog.component';
import { DGoodsRoutingModule } from './route/d-goods-routing.module';

@NgModule({
  imports: [SharedModule, DGoodsRoutingModule],
  declarations: [DGoodsComponent, DGoodsDetailComponent, DGoodsUpdateComponent, DGoodsDeleteDialogComponent],
  entryComponents: [DGoodsDeleteDialogComponent],
})
export class DGoodsModule {}
