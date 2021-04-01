jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DRkService } from '../service/d-rk.service';
import { IDRk, DRk } from '../d-rk.model';

import { DRkUpdateComponent } from './d-rk-update.component';

describe('Component Tests', () => {
  describe('DRk Management Update Component', () => {
    let comp: DRkUpdateComponent;
    let fixture: ComponentFixture<DRkUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dRkService: DRkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DRkUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DRkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DRkUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dRkService = TestBed.inject(DRkService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dRk: IDRk = { id: 456 };

        activatedRoute.data = of({ dRk });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dRk));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dRk = { id: 123 };
        spyOn(dRkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dRk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dRk }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dRkService.update).toHaveBeenCalledWith(dRk);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dRk = new DRk();
        spyOn(dRkService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dRk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dRk }));
        saveSubject.complete();

        // THEN
        expect(dRkService.create).toHaveBeenCalledWith(dRk);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dRk = { id: 123 };
        spyOn(dRkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dRk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dRkService.update).toHaveBeenCalledWith(dRk);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
