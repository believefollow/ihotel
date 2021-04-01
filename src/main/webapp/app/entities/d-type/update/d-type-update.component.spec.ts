jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DTypeService } from '../service/d-type.service';
import { IDType, DType } from '../d-type.model';

import { DTypeUpdateComponent } from './d-type-update.component';

describe('Component Tests', () => {
  describe('DType Management Update Component', () => {
    let comp: DTypeUpdateComponent;
    let fixture: ComponentFixture<DTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dTypeService: DTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dTypeService = TestBed.inject(DTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dType: IDType = { id: 456 };

        activatedRoute.data = of({ dType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dType));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dType = { id: 123 };
        spyOn(dTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dTypeService.update).toHaveBeenCalledWith(dType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dType = new DType();
        spyOn(dTypeService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dType }));
        saveSubject.complete();

        // THEN
        expect(dTypeService.create).toHaveBeenCalledWith(dType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dType = { id: 123 };
        spyOn(dTypeService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dTypeService.update).toHaveBeenCalledWith(dType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
