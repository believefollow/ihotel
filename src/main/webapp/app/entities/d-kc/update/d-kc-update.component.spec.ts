jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DKcService } from '../service/d-kc.service';
import { IDKc, DKc } from '../d-kc.model';

import { DKcUpdateComponent } from './d-kc-update.component';

describe('Component Tests', () => {
  describe('DKc Management Update Component', () => {
    let comp: DKcUpdateComponent;
    let fixture: ComponentFixture<DKcUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dKcService: DKcService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DKcUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DKcUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DKcUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dKcService = TestBed.inject(DKcService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dKc: IDKc = { id: 456 };

        activatedRoute.data = of({ dKc });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dKc));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dKc = { id: 123 };
        spyOn(dKcService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dKc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dKc }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dKcService.update).toHaveBeenCalledWith(dKc);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dKc = new DKc();
        spyOn(dKcService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dKc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dKc }));
        saveSubject.complete();

        // THEN
        expect(dKcService.create).toHaveBeenCalledWith(dKc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dKc = { id: 123 };
        spyOn(dKcService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dKc });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dKcService.update).toHaveBeenCalledWith(dKc);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
