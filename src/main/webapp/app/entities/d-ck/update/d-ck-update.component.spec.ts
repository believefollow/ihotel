jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DCkService } from '../service/d-ck.service';
import { IDCk, DCk } from '../d-ck.model';

import { DCkUpdateComponent } from './d-ck-update.component';

describe('Component Tests', () => {
  describe('DCk Management Update Component', () => {
    let comp: DCkUpdateComponent;
    let fixture: ComponentFixture<DCkUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dCkService: DCkService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DCkUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DCkUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DCkUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dCkService = TestBed.inject(DCkService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dCk: IDCk = { id: 456 };

        activatedRoute.data = of({ dCk });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dCk));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCk = { id: 123 };
        spyOn(dCkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCk }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dCkService.update).toHaveBeenCalledWith(dCk);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCk = new DCk();
        spyOn(dCkService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dCk }));
        saveSubject.complete();

        // THEN
        expect(dCkService.create).toHaveBeenCalledWith(dCk);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dCk = { id: 123 };
        spyOn(dCkService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dCk });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dCkService.update).toHaveBeenCalledWith(dCk);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
