jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DGoodsService } from '../service/d-goods.service';
import { IDGoods, DGoods } from '../d-goods.model';

import { DGoodsUpdateComponent } from './d-goods-update.component';

describe('Component Tests', () => {
  describe('DGoods Management Update Component', () => {
    let comp: DGoodsUpdateComponent;
    let fixture: ComponentFixture<DGoodsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dGoodsService: DGoodsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DGoodsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DGoodsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DGoodsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dGoodsService = TestBed.inject(DGoodsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dGoods: IDGoods = { id: 456 };

        activatedRoute.data = of({ dGoods });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dGoods));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dGoods = { id: 123 };
        spyOn(dGoodsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dGoods });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dGoods }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dGoodsService.update).toHaveBeenCalledWith(dGoods);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dGoods = new DGoods();
        spyOn(dGoodsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dGoods });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dGoods }));
        saveSubject.complete();

        // THEN
        expect(dGoodsService.create).toHaveBeenCalledWith(dGoods);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dGoods = { id: 123 };
        spyOn(dGoodsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dGoods });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dGoodsService.update).toHaveBeenCalledWith(dGoods);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
