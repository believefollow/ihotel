jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ComsetService } from '../service/comset.service';
import { IComset, Comset } from '../comset.model';

import { ComsetUpdateComponent } from './comset-update.component';

describe('Component Tests', () => {
  describe('Comset Management Update Component', () => {
    let comp: ComsetUpdateComponent;
    let fixture: ComponentFixture<ComsetUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let comsetService: ComsetService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ComsetUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ComsetUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ComsetUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      comsetService = TestBed.inject(ComsetService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const comset: IComset = { id: 456 };

        activatedRoute.data = of({ comset });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(comset));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const comset = { id: 123 };
        spyOn(comsetService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ comset });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: comset }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(comsetService.update).toHaveBeenCalledWith(comset);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const comset = new Comset();
        spyOn(comsetService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ comset });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: comset }));
        saveSubject.complete();

        // THEN
        expect(comsetService.create).toHaveBeenCalledWith(comset);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const comset = { id: 123 };
        spyOn(comsetService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ comset });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(comsetService.update).toHaveBeenCalledWith(comset);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
