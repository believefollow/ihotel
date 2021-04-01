jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DUnitService } from '../service/d-unit.service';
import { IDUnit, DUnit } from '../d-unit.model';

import { DUnitUpdateComponent } from './d-unit-update.component';

describe('Component Tests', () => {
  describe('DUnit Management Update Component', () => {
    let comp: DUnitUpdateComponent;
    let fixture: ComponentFixture<DUnitUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dUnitService: DUnitService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DUnitUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DUnitUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DUnitUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dUnitService = TestBed.inject(DUnitService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dUnit: IDUnit = { id: 456 };

        activatedRoute.data = of({ dUnit });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dUnit));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dUnit = { id: 123 };
        spyOn(dUnitService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dUnit });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dUnit }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dUnitService.update).toHaveBeenCalledWith(dUnit);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dUnit = new DUnit();
        spyOn(dUnitService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dUnit });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dUnit }));
        saveSubject.complete();

        // THEN
        expect(dUnitService.create).toHaveBeenCalledWith(dUnit);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dUnit = { id: 123 };
        spyOn(dUnitService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dUnit });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dUnitService.update).toHaveBeenCalledWith(dUnit);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
