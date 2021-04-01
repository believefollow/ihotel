import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DGoodsDetailComponent } from './d-goods-detail.component';

describe('Component Tests', () => {
  describe('DGoods Management Detail Component', () => {
    let comp: DGoodsDetailComponent;
    let fixture: ComponentFixture<DGoodsDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DGoodsDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dGoods: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DGoodsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DGoodsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dGoods on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dGoods).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
